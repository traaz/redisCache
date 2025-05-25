package com.example.denemeApi.services;


import com.example.denemeApi.model.MyModel;
import com.example.denemeApi.repository.DenemeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyModelService {

    private DenemeRepository denemeRepository;

    public MyModelService(DenemeRepository denemeRepository) {
        this.denemeRepository = denemeRepository;
    }

    @Cacheable(value = "myModels", key = "#root.methodName")
    public List<MyModel> getAll(){
       return  denemeRepository.getAll();
    }

    // Belirli bir id için cache kontrol edilir, yoksa veritabanından alınır
    @Cacheable(value = "myModel", key = "#root.methodName + #id")
    public MyModel getModelById(int id) {
        return denemeRepository.getModelById(id);
    }

    //burasi cagirldiginde myModels cache silincek. o cache tekrar getAll ile cagrildiginde dbden cekecek guncel veriyi
    @CacheEvict(value = "myModels", allEntries = true)
    public void addEntry(MyModel model) {
        denemeRepository.addEntry(model);
    }

    //burasi cagirldiginde myModels, myModels cache silincek. o cacheler tekrar getAll ile cagrildiginde dbden cekecek guncel veriyi
    @CacheEvict(value = {"myModel", "myModels"}, allEntries = true)
    public void updateModel(MyModel model, int id) {
        denemeRepository.updateModel(model, id);
    }

}
