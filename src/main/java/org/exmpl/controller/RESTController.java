package org.exmpl.controller;

import lombok.RequiredArgsConstructor;
import org.exmpl.exceptions.FieldCreationFailure;
import org.exmpl.service.SudokuBF;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(RESTController.URI)
public class RESTController {

    public static final String URI = "/solve";
    private final SudokuBF sbf;

    @PostMapping
    public String solveSudokuField(@RequestBody String field) {
        Objects.requireNonNull(field);
        try {
            List<String> solutions = sbf.solveSudoku(field);
            if (solutions.size() > 0)
                return solutions.get(0);
            else
                return "{ \"error\" : \"Sudoku is unsolvable\" }";
        } catch (FieldCreationFailure exception) {
            return "{ \"error\" : \"" + exception.getMessage() + "\" }";
        }
    }
}
