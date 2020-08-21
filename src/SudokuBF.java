public class SudokuBF {

    public void build(Field startField) {
        reqInsert(startField);
    }

    private void reqInsert(Field field) {
        int xx = 0;
        int yy = 0;
        try {
            Field tmpField = (Field)field.clone();
            stop_search: {
                for (int y = 0; y < Field.FIELD_LENGTH; y++)
                    for (int x = 0; x < Field.FIELD_LENGTH; x++)
                        if (field.getCellValue(x, y) == -1) {
                            xx = x;
                            yy = y;
                            break stop_search;
                        }
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println(field.toString());
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                return;
            }
            for (int i = 1; i < 10; i++) {
                if (tmpField.setDigit(i, xx, yy)) {
                    reqInsert(tmpField);
                    tmpField = (Field)field.clone();
                }
            }
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
}