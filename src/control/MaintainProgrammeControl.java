package control;

import da.ProgrammeDA;
import domain.Programme;

public class MaintainProgrammeControl {

    private ProgrammeDA progDA;

    public MaintainProgrammeControl() {
        progDA = new ProgrammeDA();
    }

    public Programme selectRecord(String code) {
        return progDA.getRecord(code);
    }
    
    public void addRecord(Programme p) {
        progDA.addRecord(p);
    }
    
    public void updateRecord(Programme p) {
        progDA.updateRecord(p);
    }
    
    public void deleteRecord(String code) {
        progDA.deleteRecord(code);
    }

}
