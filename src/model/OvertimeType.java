package model;

public class OvertimeType {
    private int overtimeTypeId;
    private String overtimeTypeName;
    private double multiplier;

    public OvertimeType(int overtimeTypeId, String overtimeTypeName, double multiplier) {
        this.overtimeTypeId = overtimeTypeId;
        this.overtimeTypeName = overtimeTypeName;
        this.multiplier = multiplier;
    }

    public int getOvertimeTypeId() {
        return overtimeTypeId;
    }

    public void setOvertimeTypeId(int overtimeTypeId) {
        this.overtimeTypeId = overtimeTypeId;
    }

    public String getOvertimeTypeName() {
        return overtimeTypeName;
    }

    public void setOvertimeTypeName(String overtimeTypeName) {
        this.overtimeTypeName = overtimeTypeName;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}
