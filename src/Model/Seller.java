package Model;

import java.time.LocalDateTime;

public class Seller {

    private int identificador;
    private LocalDateTime cadastroDataHora;
    private String nome;
    private String tr;

    public Seller(LocalDateTime cadastroDataHora, String nome, String tr) {

        this.cadastroDataHora = cadastroDataHora;
        this.nome = nome;
        this.tr = tr;
    }

    public Seller(int identificador, LocalDateTime cadastroDataHora, String nome, String tr) {
        this.identificador = identificador;
        this.cadastroDataHora = cadastroDataHora;
        this.nome = nome;
        this.tr = tr;
    }

    public Seller(int identificador) {
        this.identificador = identificador;
    }

    public Seller(int identificador, String tr) {
        this.identificador = identificador;
        this.tr = tr;
    }

    public Seller(String tr) {
        this.tr = tr;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public LocalDateTime getCadastroDataHora() {
        return cadastroDataHora;
    }

    public void setCadastroDataHora(LocalDateTime cadastroDataHora) {
        this.cadastroDataHora = cadastroDataHora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTr() {
        return tr;
    }

    public void setTr(String tr) {
        this.tr = tr;
    }

}
