package Model;

import java.time.LocalDateTime;

public class Vendedor {
    
    private int identificador;
    private LocalDateTime cadastroDataHora;
    private String nome;
    private String sobreNome;
    private String tr;

    public Vendedor(LocalDateTime cadastroDataHora, String nome, String sobreNome, String tr) {
       
        this.cadastroDataHora = cadastroDataHora;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.tr = tr;
    }
    
    public Vendedor(int  identificador){
         this.identificador = identificador;
    }
    
    public Vendedor(String  tr){
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

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getTr() {
        return tr;
    }

    public void setTr(String tr) {
        this.tr = tr;
    }
    
    
    
}
