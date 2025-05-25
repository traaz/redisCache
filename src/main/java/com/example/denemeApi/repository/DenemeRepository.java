package com.example.denemeApi.repository;


import com.example.denemeApi.model.MyModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DenemeRepository {

    private JdbcTemplate jdbcTemplate;
    private  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DenemeRepository(JdbcTemplate jdbcTemplate,NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<MyModel> getAll(){
        String getAllQuery = "SELECT * FROM DENEME";
        List<MyModel>  myModelList = new ArrayList<>();
        jdbcTemplate.query(getAllQuery, rs -> { //resultSet
            int id = rs.getInt("ID");
            String name = rs.getString("NAME");
            String code = rs.getString("CODE");

            myModelList.add(new MyModel(code, name, id));

        });
        return myModelList;

    }

    public void updateModel(MyModel model, int id){
        String updateQuery = "UPDATE DENEME SET name = :nameValue, code = :codeValue WHERE id = :idValue";

        Map<String, Object> params = new HashMap<>();
        params.put("nameValue", model.getName());
        params.put("codeValue", model.getCode());
        params.put("idValue", id);

        namedParameterJdbcTemplate.update(updateQuery, params);
    }

    public MyModel getModelById(int id){
        String getModelByIdQuery = "SELECT * FROM DENEME WHERE id=:idValue";
        Map<String, Object> params = new HashMap<>();
        params.put("idValue", id);
        MapSqlParameterSource paramSource = new MapSqlParameterSource(params);
        return namedParameterJdbcTemplate.queryForObject(getModelByIdQuery, paramSource,  new BeanPropertyRowMapper<>(MyModel.class));

    }

    public void addEntry(MyModel myModel){
        String yeniCode;
        int kayitSayisi = kacTaneVar(myModel.getCode());
        if(kayitSayisi == 0){
            yeniCode = myModel.getCode();
        }else{
            yeniCode = myModel.getCode() + "/" + (kayitSayisi+ 1);
        }

        String addQuery = "INSERT INTO DENEME (name, code) VALUES (:nameValue, :codeValue)";

        Map<String, Object> insertParams = new HashMap<>();
        insertParams.put("nameValue", myModel.getName());
        insertParams.put("codeValue", yeniCode);

        MapSqlParameterSource insertParamSource = new MapSqlParameterSource(insertParams);

        namedParameterJdbcTemplate.update(addQuery, insertParamSource);
    }
    public int kacTaneVar(String code){
        String countQuery= "SELECT COUNT(*) FROM DENEME WHERE code LIKE :likeValue";

        Map<String, Object> countParams = new HashMap<>();
        countParams.put("likeValue", "%" + code + "%");

        MapSqlParameterSource countParamSource = new MapSqlParameterSource(countParams);

        return namedParameterJdbcTemplate.queryForObject(countQuery, countParamSource, Integer.class);
    }
}
