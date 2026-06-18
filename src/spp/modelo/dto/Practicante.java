package spp.modelo.dto;

public class Practicante {

    private int idPracticante;
    private String matricula;
    private String sexo;
    private String lenguaIndigena;
    private int creditosAcumulados;
    private boolean seguroMedicoVigente;
    private Curso curso;
    private Usuario usuario;

    public Practicante() {
    }

    public Practicante(int idPracticante, String matricula, String sexo, String lenguaIndigena, int creditosAcumulados, boolean seguroMedicoVigente, Curso curso, Usuario usuario) {
        this.idPracticante = idPracticante;
        this.matricula = matricula;
        this.sexo = sexo;
        this.lenguaIndigena = lenguaIndigena;
        this.creditosAcumulados = creditosAcumulados;
        this.seguroMedicoVigente = seguroMedicoVigente;
        this.curso = curso;
        this.usuario = usuario;
    }

    public int getIdPracticante() {
        return idPracticante;
    }

    public void setIdPracticante(int idPracticante) {
        this.idPracticante = idPracticante;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getLenguaIndigena() {
        return lenguaIndigena;
    }

    public void setLenguaIndigena(String lenguaIndigena) {
        this.lenguaIndigena = lenguaIndigena;
    }

    public int getCreditosAcumulados() {
        return creditosAcumulados;
    }

    public void setCreditosAcumulados(int creditosAcumulados) {
        this.creditosAcumulados = creditosAcumulados;
    }

    public boolean isSeguroMedicoVigente() {
        return seguroMedicoVigente;
    }

    public void setSeguroMedicoVigente(boolean seguroMedicoVigente) {
        this.seguroMedicoVigente = seguroMedicoVigente;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
