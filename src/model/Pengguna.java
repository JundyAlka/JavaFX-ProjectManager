package model;

public class Pengguna {
    private int id;
    private String namaLengkap;
    private String nim;
    private String noHp;
    private String email;
    private String jenisKelamin;

    public Pengguna() {
    }

    public Pengguna(String namaLengkap, String nim, String noHp, String email, String jenisKelamin) {
        this.namaLengkap = namaLengkap;
        this.nim = nim;
        this.noHp = noHp;
        this.email = email;
        this.jenisKelamin = jenisKelamin;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
}
