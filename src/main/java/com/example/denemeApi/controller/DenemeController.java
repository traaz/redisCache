package com.example.denemeApi.controller;

import com.example.denemeApi.model.MyModel;
import com.example.denemeApi.repository.DenemeRepository;
import com.example.denemeApi.services.MyModelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DenemeController {
    private MyModelService modelService;
    public DenemeController(MyModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping()
    public List<MyModel> getAll(){
        return modelService.getAll();
    }

    @PostMapping
    public void addEntry(@RequestBody MyModel model){
       modelService.addEntry(model);
    }
    @GetMapping("/{id}")
    public MyModel getModelById(@PathVariable int id){
        return modelService.getModelById(id);
    }
    @PutMapping("/{id}")
    public void updateModel(@RequestBody MyModel model, @PathVariable int id){
        modelService.updateModel(model, id);
    }
}
