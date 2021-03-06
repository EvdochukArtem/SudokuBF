public class SudokuBF {

    public void build(Field startField) {
        reqInsert(startField);
    }

    private void reqInsert(Field field) {
        int xx;
        int yy;

        try {
            Field tmpField = field.clone();
            stop_search: {
                for (int y = 0; y < Field.FIELD_LENGTH; y++)
                    for (int x = 0; x < Field.FIELD_LENGTH; x++)
                        if (field.getCellValue(x, y) == -1) {
                            xx = x;
                            yy = y;
                            break stop_search;
                        }
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.print(field.toString());
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                return;
            }
            for (int i = 1; i < 10; i++) {
                if (tmpField.setDigit(i, xx, yy)) {
                    reqInsert(tmpField);
                    tmpField = field.clone();
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); //Знаю что так делать не хорошо, слышал что лучше оборачивать в UnckeckedExceptions
                                 //но хз в какой уместнее.
        }
    }
}