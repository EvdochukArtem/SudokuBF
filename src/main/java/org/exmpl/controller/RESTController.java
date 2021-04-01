package org.exmpl.controller;

import lombok.RequiredArgsConstructor;
import org.exmpl.exceptions.FieldCreationFailure;
import org.exmpl.exceptions.FieldDBExtractionFailure;
import org.exmpl.exceptions.FieldSavingFailure;
import org.exmpl.service.SudokuBF;
import org.exmpl.service.dao.FieldDao;
import org.json.JSONObject;
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
        String f = "field";
        String e = "error";
        try {
            List<String> solutions = sbf.solveSudoku(field);
            if (solutions.size() > 0) {
                fieldDao.saveField(new JSONObject(field).getString(f)); //TODO: Need to warn user about saving his data
                return new JSONObject().put(f, solutions.get(0)).toString();
            }
            else
                return new JSONObject().put(e, "Sudoku is unsolvable").toString();
        } catch (FieldCreationFailure exception) {
            return new JSONObject().put( e, exception.getMessage()).toString();
        }
    }

    @GetMapping
    public String getRandomSudokuField() throws FieldDBExtractionFailure {
        return new JSONObject().put("field", fieldDao.getRandomField().toString()).toString();
    }
}
