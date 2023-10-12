package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.AdDto;
import com.example.bulletinboard.dto.CreateOrUpdateAd;
import com.example.bulletinboard.repository.AdRepo;
import com.example.bulletinboard.service.AdMapper;
import com.example.bulletinboard.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepo adRepo;
    private final AdMapper adMapper;

    @Override
    public AdDto create(AdDto ad) {
        return adMapper.toDto(adRepo.save(adMapper.toAd(ad)));
    }

    @Override
    public Optional<AdDto> getById(Integer id) {
        return Optional.ofNullable(adMapper.toDto(adRepo.findById(id).orElse(null)));
    }

    @Override
    public AdDto updateImage(Integer id) {
        return adMapper.toDto(adRepo.findById(id).orElse(null));
    }

    @Override
    public List<AdDto> getAll() {
        return adRepo.findAll()
                .stream()
                .map(adMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAd(Integer adId, CreateOrUpdateAd ad) {
        AtomicReference<Optional<CreateOrUpdateAd>> atomicReference = new AtomicReference<>();
        adRepo.findById(adId).ifPresentOrElse(foundAd -> {
            foundAd.setTittle(ad.getTitle());
            foundAd.setPrice(ad.getPrice());
            foundAd.setDescription(ad.getDescription());
            atomicReference.set(Optional.of(adMapper.fromUpdateAd(adRepo.save(foundAd))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
    }

    @Override
    public Boolean deleteByID(Integer adId) {
        if (adRepo.existsById(adId)) {
            adRepo.deleteById(adId);
            return true;
        }
        return false;

    }
}