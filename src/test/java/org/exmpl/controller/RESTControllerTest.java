package org.exmpl.controller;

import org.exmpl.TestData;
import org.exmpl.service.SudokuBF;
import org.exmpl.service.dao.FieldDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RESTControllerTestConfiguration.class)
@AutoConfigureMockMvc
class RESTControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RESTController controller;

    @MockBean
    SudokuBF sbf;

    @MockBean
    FieldDao dao;

    @Test
    void solveSudokuField() throws Exception {
        String inputJSON = TestData.SUDOKU_INCOMPLETE_JSON;
        ResultActions actions = mockMvc.perform(
            MockMvcRequestBuilders
                    .post(RESTController.URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(inputJSON)
        ).andExpect(status().isOk());
        Mockito.verify(sbf, Mockito.times(1)).solveSudoku(inputJSON);
    }

    @Test
    void getRandomSudokuField() throws Exception {
        Mockito.when(dao.getRandomField()).thenReturn(TestData.FIELD_INCOMPLETE);
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get(RESTController.URI)
        ).andExpect(status().isOk());
        Mockito.verify(dao, Mockito.times(1)).getRandomField();
    }
}