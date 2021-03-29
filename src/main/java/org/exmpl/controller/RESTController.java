package org.exmpl.controller;

import lombok.RequiredArgsConstructor;
import org.exmpl.exceptions.FieldCreationFailure;
import org.exmpl.service.SudokuBF;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(RESTController.URI)
public class RESTController {

    public static final String URI = "/solve";

    @PostMapping
    public String solveSudokuField(HttpEntity<byte[]> requestEntity) {
        Objects.requireNonNull(requestEntity);
        Objects.requireNonNull(requestEntity.getBody());
        String jsonFieldToSolve = new String(requestEntity.getBody());
        SudokuBF sbf = new SudokuBF();
        try {
            List<String> solutions = sbf.solveSudoku(jsonFieldToSolve);
            if (solutions.size() > 0)
                return solutions.get(0);
            else
                return "Sudoku is unsolvable";
        } catch (FieldCreationFailure exception) {
            return exception.getMessage();
        }
    }
}
