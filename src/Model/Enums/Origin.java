/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Model.Enums;

public enum Origin {
      SELECT("Selecione"),
    BASE("Base"),
    CHAT("Chat"),
    INDICATION("Indicação"),;
    
    private String type;
    
    
    private Origin(String type) {  
    this.type = type;    
    }

    @Override
    public String toString() {
        return type;
    }
    
    
    
}
