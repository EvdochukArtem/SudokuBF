package org.exmpl.controller;

import lombok.RequiredArgsConstructor;
import org.exmpl.exceptions.FieldCreationFailure;
import org.exmpl.exceptions.FieldDBExtractionFailure;
import org.exmpl.exceptions.FieldSavingFailure;
import org.exmpl.service.SudokuBF;
import org.exmpl.service.dao.FieldDao;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(RESTController.URI)
public class RESTController {

    public static final String URI = "/solve";

    private final SudokuBF sbf;
    private final FieldDao fieldDao;

    @PostMapping
    public String solveSudokuField(@RequestBody String field) throws FieldSavingFailure {
        Objects.requireNonNull(field);
        try {
            List<String> solutions = sbf.solveSudoku(field);
            if (solutions.size() > 0) {
                fieldDao.saveField(field.replaceAll("\\D","")); //TODO: Need to warn user about saving his data
                return solutions.get(0);
            }
            else
                return "{ \"error\" : \"Sudoku is unsolvable\" }";
        } catch (FieldCreationFailure exception) {
            return "{ \"error\" : \"" + exception.getMessage() + "\" }";
        }
    }

    @GetMapping
    public String getRandomSudokuField() throws FieldDBExtractionFailure {
        return fieldDao.getRandomField().toJSONString();
    }
}
