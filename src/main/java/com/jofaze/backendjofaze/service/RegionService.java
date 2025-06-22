package com.jofaze.backendjofaze.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jofaze.backendjofaze.model.Region;
import com.jofaze.backendjofaze.repository.RegionRepository;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    public Optional<Region> findById(Long id) {
        return regionRepository.findById(id);
    }

    public Region save(Region region) {
        return regionRepository.save(region);
    }

    public void deleteById(Long id) {
        regionRepository.deleteById(id);
    }

    public Region actualizarRegion(Long id, Region region) {
        Region existente = regionRepository.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(region.getNombre());
            return regionRepository.save(existente);
        } else {
            return null;
        }
    }

    public Region patchRegion(Long id, Region region) {
        Region existente = regionRepository.findById(id).orElse(null);
        if (existente != null) {
            if (region.getNombre() != null) existente.setNombre(region.getNombre());
            return regionRepository.save(existente);
        } else {
            return null;
        }
    }
}