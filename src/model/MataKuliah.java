package model;

public class MataKuliah {
    private int id;
    private String kodeMatakuliah;
    private String namaMatakuliah;
    private int sks;

    public MataKuliah() {
    }

    public MataKuliah(String kodeMatakuliah, String namaMatakuliah, int sks) {
        this.kodeMatakuliah = kodeMatakuliah;
        this.namaMatakuliah = namaMatakuliah;
        this.sks = sks;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodeMatakuliah() {
        return kodeMatakuliah;
    }

    public void setKodeMatakuliah(String kodeMatakuliah) {
        this.kodeMatakuliah = kodeMatakuliah;
    }

    public String getNamaMatakuliah() {
        return namaMatakuliah;
    }

    public void setNamaMatakuliah(String namaMatakuliah) {
        this.namaMatakuliah = namaMatakuliah;
    }

    public int getSks() {
        return sks;
    }

    public void setSks(int sks) {
        this.sks = sks;
    }
}
