package com.isst.nutriapp.grupo18.apirest.JSON;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatosJSON {
    private ArrayList<ArrayList<String>> resp;
}
