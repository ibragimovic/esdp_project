package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.LocalitiesDTO;
import com.esdp.demo_esdp.entity.Localities;
import com.esdp.demo_esdp.exception.ResourceNotFoundException;
import com.esdp.demo_esdp.repositories.LocalitiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocalitiesService {
    private final LocalitiesRepository localitiesRepository;

    public void addNewLocality(String name, Localities parentLocality){
        localitiesRepository.save(Localities.builder()
                        .name(name)
                        .parent(parentLocality)
                .build());
    }

    public void addNewLocality(String name, Long parentId){
        localitiesRepository.save(Localities.builder()
                .name(name)
                .parent(getLocalityById(parentId))
                .build());
    }

    public void deleteLocalityById(Long localityId){
        localitiesRepository.deleteById(localityId);
    }

    public void changeLocalityName(Long localityId,String newName){
        Optional<Localities> localOpt=getLocalityOptById(localityId);
        if (localOpt.isPresent()){
            Localities local=localOpt.get();
            local.setName(newName);
            localitiesRepository.save(local);
        }
    }

    public void changeLocalityParent(Long localityId, Long newParentId){
        Optional<Localities> changeLocalOpt=getLocalityOptById(localityId);
        Optional<Localities> parentLocalOpt=getLocalityOptById(newParentId);

        if(changeLocalOpt.isPresent() && parentLocalOpt.isPresent()){
            Localities changeLocal=changeLocalOpt.get();

            changeLocal.setParent(parentLocalOpt.get());
            localitiesRepository.save(changeLocal);
        }


    }

    public void changeLocality(Long localityId, Long newParentId, String newName){
        Optional<Localities> changeLocalOpt=getLocalityOptById(localityId);
        Optional<Localities> parentLocalOpt=getLocalityOptById(newParentId);

        if(changeLocalOpt.isPresent() && parentLocalOpt.isPresent()){
            Localities changeLocal=changeLocalOpt.get();

            changeLocal.setParent(parentLocalOpt.get());
            changeLocal.setName(newName);
            localitiesRepository.save(changeLocal);
        }
    }

    //this method will be modified
    public List<LocalitiesDTO> getLocalitiesDTOs(){
        return localitiesRepository.findAllChildren().stream().map(LocalitiesDTO::from).collect(Collectors.toList());
    }

    //this method will be modified
    public List<String> getLocalitiesNames(Long userId, Long productId){
        return localitiesRepository.findAll().stream().map(Localities::getName).collect(Collectors.toList());
    }


    protected Localities getLocalityById(Long id) throws ResourceNotFoundException{
        return localitiesRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(String.format("Locality with id:%s not found",id))
        );
    }

    public LocalitiesDTO getLocalityDTOById(Long id) throws ResourceNotFoundException{
        return LocalitiesDTO.from(getLocalityById(id));
    }

    private Optional<Localities> getLocalityOptById(Long id){
        return localitiesRepository.findById(id);
    }

}
