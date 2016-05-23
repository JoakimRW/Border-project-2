/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luffarserver;

/**
 *
 * @author Reza
 */
public class GameHandler {
    
    private int[] array = new int[400];
    
    
    public GameHandler(){
        for(int i = 0 ; i < 400 ; i++){
            array[i] = 0;
        }
    }
    
    public void checkWin(int boxClicked, int playerNumber){
        
        int eastnorth = 0;
        int southnorth = 0;
        int westnorth = 0;
        int westeast = 0;
        int northeast = 0;
        int northsouth = 0;
        int northwest = 0;
        int eastwest = 0;
        
        //array[76] = 2;
        
        array[boxClicked - 1] = playerNumber;
        
        if((boxClicked -1 -21 >= 0) && (boxClicked != 21) && (boxClicked != 41) && (boxClicked != 61) && (boxClicked != 81) &&
           (boxClicked != 101) && (boxClicked != 121) && (boxClicked != 141) && (boxClicked != 161) &&
           (boxClicked != 181) && (boxClicked != 201) && (boxClicked != 221) && (boxClicked != 241)  &&
           (boxClicked != 261) && (boxClicked != 281) && (boxClicked != 301) && (boxClicked != 321)  &&
           (boxClicked != 341) && (boxClicked != 361) && (boxClicked != 381)  ) {
            if(array[boxClicked-1-21] == playerNumber){
                eastnorth++;
            }
           
        }
        
        if((boxClicked -1 -20 >= 0) && (boxClicked >=1 && boxClicked <= 20) ){
            if(array[boxClicked-1-20] == playerNumber){
                southnorth++;
            }
        }
        
        if((boxClicked -1 -19 >= 0) && (boxClicked != 20) && (boxClicked != 40) && (boxClicked != 60) && (boxClicked != 80) &&
           (boxClicked != 100) && (boxClicked != 120) && (boxClicked != 140) && (boxClicked != 160) &&
           (boxClicked != 180) && (boxClicked != 200) && (boxClicked != 220) && (boxClicked != 240)  &&
           (boxClicked != 260) && (boxClicked != 280) && (boxClicked != 300) && (boxClicked != 320)  &&
           (boxClicked != 340) && (boxClicked != 360) && (boxClicked != 380) && (boxClicked != 400)  ){
            if(array[boxClicked-1-19] == playerNumber){
                westnorth++;
            }
        }
        
        if((boxClicked -1 +1 >= 0) && (boxClicked != 20) && (boxClicked != 40) && (boxClicked != 60) && (boxClicked != 80) &&
           (boxClicked != 100) && (boxClicked != 120) && (boxClicked != 140) && (boxClicked != 160) &&
           (boxClicked != 180) && (boxClicked != 200) && (boxClicked != 220) && (boxClicked != 240)  &&
           (boxClicked != 260) && (boxClicked != 280) && (boxClicked != 300) && (boxClicked != 320)  &&
           (boxClicked != 340) && (boxClicked != 360) && (boxClicked != 380) && (boxClicked != 400)  ){
            if(array[boxClicked-1+1] == playerNumber){
                westeast++;
            }
        }
        
        if((boxClicked -1 +21 >= 0) && (boxClicked != 40) && (boxClicked != 60) && (boxClicked != 80) &&
           (boxClicked != 100) && (boxClicked != 120) && (boxClicked != 140) && (boxClicked != 160) &&
           (boxClicked != 180) && (boxClicked != 200) && (boxClicked != 220) && (boxClicked != 240)  &&
           (boxClicked != 260) && (boxClicked != 280) && (boxClicked != 300) && (boxClicked != 320)  &&
           (boxClicked != 340) && (boxClicked != 360) && (boxClicked != 380) && (boxClicked != 400) && (boxClicked <=379)){
            if(array[boxClicked-1+21] == playerNumber){
                northeast++;
            }
        }
        
        
        if((array[boxClicked-1+20] == playerNumber) && (boxClicked <= 380)){
            northsouth++;
        }
        
        if((boxClicked -1 +19 >= 0) && (boxClicked != 21) && (boxClicked != 41) && (boxClicked != 61) && (boxClicked != 81) &&
           (boxClicked != 101) && (boxClicked != 121) && (boxClicked != 141) && (boxClicked != 161) &&
           (boxClicked != 181) && (boxClicked != 201) && (boxClicked != 221) && (boxClicked != 241)  &&
           (boxClicked != 261) && (boxClicked != 281) && (boxClicked != 301) && (boxClicked != 321)  &&
           (boxClicked != 341) && (boxClicked != 361) && (boxClicked != 381) && (boxClicked <= 380) ) {
            if(array[boxClicked-1+19] == playerNumber){
                northwest++;
            }
           
        }
        
        if((boxClicked -1 -1 >= 0) && (boxClicked != 21) && (boxClicked != 41) && (boxClicked != 61) && (boxClicked != 81) &&
           (boxClicked != 101) && (boxClicked != 121) && (boxClicked != 141) && (boxClicked != 161) &&
           (boxClicked != 181) && (boxClicked != 201) && (boxClicked != 221) && (boxClicked != 241)  &&
           (boxClicked != 261) && (boxClicked != 281) && (boxClicked != 301) && (boxClicked != 321)  &&
           (boxClicked != 341) && (boxClicked != 361) && (boxClicked != 381)  ) {
            if(array[boxClicked-1-1] == playerNumber){
            eastwest++;
            }
           
        }
        
        
     
        
      //System.out.println(westnorth);
                
    }
    
}
