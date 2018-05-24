import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import oscP5.*; 
import netP5.*; 
import controlP5.*; 
import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Marvel_6Light extends PApplet {




int pixel_size = 5;

PowerStream []six_gemstone;

PowerStream space; // blue
PowerStream soul; // orange
PowerStream reality; // red
PowerStream time; // green
PowerStream power; // purple
PowerStream mind; // yellow

OscP5 oscP5;
NetAddress other;



ControlP5 cp5;

public void setup(){
  //size(426,18);
  
  //size(660,30);
  
  //size(18,426);
  six_gemstone = new PowerStream[6];
  six_gemstone[0] = new PowerStream(48,48,36, color(0,0,255));
  six_gemstone[1] = new PowerStream(48,48,36, color(255,172,18));
  six_gemstone[2] = new PowerStream(48,48,36, color(255,0,0));
  six_gemstone[3] = new PowerStream(48,48,36, color(0,255,0));
  six_gemstone[4] = new PowerStream(48,48,36, color(198,120,255));
  six_gemstone[5] = new PowerStream(48,48,36, color(255,255,0));
  
  
  oscP5 = new OscP5(this,12000);
  
  other = new NetAddress("127.0.0.1",10000);
  oscP5.plug(this,"set_black","/black");
  oscP5.plug(this,"set_trigger","/trigger");
  oscP5.plug(this,"set_clean","/clean");
  
  cp5 = new ControlP5(this);
  
  Group g1 = cp5.addGroup("effect_setting")
                .setPosition(100,50)
                .setWidth(600)
                .setBackgroundHeight(250)
                .setBackgroundColor(color(255,50))
                ;
  
  List level = Arrays.asList("Level 1","Level 2","Level 3","Level 4","Level 5");
  
  cp5.addTextlabel("trigger_label1")
     .setText("Trigger Segment 1")
     .setPosition(10,10)
     .setColorValue(0xffffff00)
     .setGroup(g1)
     ;
  cp5.addTextlabel("trigger_label2")
     .setText("Trigger Segment 2")
     .setPosition(120,10)
     .setColorValue(0xffffff00)
     .setGroup(g1)
     ;
  cp5.addTextlabel("trigger_label3")
     .setText("Trigger Segment 3")
     .setPosition(230,10)
     .setColorValue(0xffffff00)
     .setGroup(g1)
     ;
  cp5.addTextlabel("clean_label")
     .setText("Clean Segment")
     .setPosition(340,10)
     .setColorValue(0xffffff00)
     .setGroup(g1)
     ;
  cp5.addTextlabel("fadeout_label")
     .setText("Fadeout")
     .setPosition(450,10)
     .setColorValue(0xffffff00)
     .setGroup(g1)
     ;
  cp5.addTextlabel("breath_label")
     .setText("Breath range")
     .setPosition(10,160)
     .setColorValue(0xffffff00)
     .setGroup(g1)
     ;
  cp5.addScrollableList("segment1_speed")
     .setPosition(10, 30)
     .setSize(100, 150)
     .setBarHeight(20)
     .setItemHeight(20)
     .addItems(level)
     .setGroup(g1)
     ;
  cp5.addScrollableList("segment2_speed")
     .setPosition(120, 30)
     .setSize(100, 150)
     .setBarHeight(20)
     .setItemHeight(20)
     .addItems(level)
     .setGroup(g1)
     ;
  cp5.addScrollableList("segment3_speed")
     .setPosition(230, 30)
     .setSize(100, 150)
     .setBarHeight(20)
     .setItemHeight(20)
     .addItems(level)
     .setGroup(g1)
     ;
     
  cp5.addScrollableList("clean_speed")
     .setPosition(340, 30)
     .setSize(100, 150)
     .setBarHeight(20)
     .setItemHeight(20)
     .addItems(level)
     .setGroup(g1)
     ;
  
  cp5.addScrollableList("fadeout_speed")
     .setPosition(450, 30)
     .setSize(100, 150)
     .setBarHeight(20)
     .setItemHeight(20)
     .addItems(level)
     .setGroup(g1)
     ;
  cp5.addRange("segment3_range")
     // disable broadcasting since setRange and setRangeValues will trigger an event
     .setBroadcast(false) 
     .setPosition(10,180)
     .setSize(430,40)
     .setHandleSize(30)
     .setRange(0,255)
     .setRangeValues(0,255)
     .setCaptionLabel("")
     //.setLabelVisible(false)
     // after the initialization we turn broadcast back on again
     .setBroadcast(true)
     .setColorForeground(color(255,40))
     .setColorBackground(color(255,40))  
     .setGroup(g1)
     ;
  
  Group g2 = cp5.addGroup("color_setting")
                .setPosition(100,350)
                .setWidth(450)
                .setBackgroundHeight(250)
                .setBackgroundColor(color(255,50))
                ;
  
  cp5.addTextlabel("alpha_text")
     .setText("Alpha")
     .setPosition(10,10)
     .setColorValue(0xffffff00)
     .setGroup(g2)
     ;
  cp5.addTextlabel("channel_1")
     .setText("Channel 1")
     .setPosition(10,70)
     .setColorValue(0xffffff00)
     .setGroup(g2)
     ;
  cp5.addTextlabel("channel_2")
     .setText("Channel 2")
     .setPosition(150,70)
     .setColorValue(0xffffff00)
     .setGroup(g2)
     ;
  cp5.addTextlabel("channel_3")
     .setText("Channel 3")
     .setPosition(290,70)
     .setColorValue(0xffffff00)
     .setGroup(g2)
     ;
   cp5.addTextlabel("channel_4")
     .setText("Channel 4")
     .setPosition(10,70+60)
     .setColorValue(0xffffff00)
     .setGroup(g2)
     ;
  cp5.addTextlabel("channel_5")
     .setText("Channel 5")
     .setPosition(150,70+60)
     .setColorValue(0xffffff00)
     .setGroup(g2)
     ;
  cp5.addTextlabel("channel_6")
     .setText("Channel 6")
     .setPosition(290,70+60)
     .setColorValue(0xffffff00)
     .setGroup(g2)
     ;
     
  cp5.addSlider("alpha")
     .setPosition(10,30)
     .setSize(200,20)
     .setRange(0,1)
     //.setValue(1)
     .setGroup(g2)
     ;
     
  cp5.addSlider("R_1")
     .setCaptionLabel("R")
     .setPosition(10,80)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  cp5.addSlider("G_1")
     .setCaptionLabel("G")
     .setPosition(10,90)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  cp5.addSlider("B_1")
     .setCaptionLabel("B")
     .setPosition(10,100)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  //
  cp5.addSlider("R_2")
     .setCaptionLabel("R")
     .setPosition(150,80)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  cp5.addSlider("G_2")
     .setCaptionLabel("G")
     .setPosition(150,90)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  cp5.addSlider("B_2")
     .setCaptionLabel("B")
     .setPosition(150,100)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
    //
  cp5.addSlider("R_3")
     .setCaptionLabel("R")
     .setPosition(290,80)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  cp5.addSlider("G_3")
     .setCaptionLabel("G")
     .setPosition(290,90)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  cp5.addSlider("B_3")
     .setCaptionLabel("B")
     .setPosition(290,100)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  //
    cp5.addSlider("R_4")
     .setCaptionLabel("R")
     .setPosition(10,80+60)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  cp5.addSlider("G_4")
     .setCaptionLabel("G")
     .setPosition(10,90+60)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  cp5.addSlider("B_4")
     .setCaptionLabel("B")
     .setPosition(10,100+60)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  //
  cp5.addSlider("R_5")
     .setCaptionLabel("R")
     .setPosition(150,80+60)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  cp5.addSlider("G_5")
     .setCaptionLabel("G")
     .setPosition(150,90+60)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  cp5.addSlider("B_5")
     .setCaptionLabel("B")
     .setPosition(150,100+60)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
    //
  cp5.addSlider("R_6")
     .setCaptionLabel("R")
     .setPosition(290,80+60)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  cp5.addSlider("G_6")
     .setCaptionLabel("G")
     .setPosition(290,90+60)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  cp5.addSlider("B_6")
     .setCaptionLabel("B")
     .setPosition(290,100+60)
     .setSize(100,10)
     .setRange(0,255)
     .setGroup(g2)
     ;
  
  cp5.addTextlabel("Info")
     .setText("Info : Pressed 's' to save setting.")
     .setPosition(100,600)
     .setColorValue(0xffffffff)
     .setFont(createFont("Georgia",15))
     ;
  
  cp5.loadProperties(("Marvel_light.properties"));
  //cp5.loadProperties(("source/Marvel_light.properties"));
  init();
}

public void set_black(int id){
  print("breath" + id);
  six_gemstone[id].setMode(0);
}

public void set_trigger(int id){
  print("trigger" + id);
  six_gemstone[id].setMode(1);
}

public void set_clean(int id){
  print("clean" + id);
  six_gemstone[id].setMode(2);
}

// CONTRAL PANEL 
public void segment1_speed(int level){
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].set_trigger_segment1_speed(level);
}

public void segment2_speed(int level){
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].set_trigger_segment2_speed(level);
}

public void segment3_speed(int level){
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].set_trigger_segment3_speed(level);
}

public void controlEvent(ControlEvent theControlEvent) {
  if(theControlEvent.isFrom("segment3_range")) {
    int colorMin = PApplet.parseInt(theControlEvent.getController().getArrayValue(0));
    int colorMax = PApplet.parseInt(theControlEvent.getController().getArrayValue(1));
    
    for(int i = 0 ; i < 6 ; i++)
      six_gemstone[i].set_trigger_segment3_range(colorMin , colorMax);
  }
}



public void clean_speed(int level){
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].set_clean_speed(level);
}

public void fadeout_speed(int level){
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].set_fadeout_speed(level);
}
public void alpha(float a){
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].set_alpha(a);
}

public void draw(){
  background(0); 
  noStroke();
  
  
  pushMatrix();
  translate(50,0);
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].draw();
  popMatrix();
}

public void init(){
  
  int segment1_speed = PApplet.parseInt(cp5.get("segment1_speed").getValue());
  int segment2_speed = PApplet.parseInt(cp5.get("segment2_speed").getValue());
  int segment3_speed = PApplet.parseInt(cp5.get("segment3_speed").getValue());
  int clean_speed = PApplet.parseInt(cp5.get("clean_speed").getValue());
  int alpha_min = PApplet.parseInt(cp5.get("segment3_range").getArrayValue(0));
  int alpha_max = PApplet.parseInt(cp5.get("segment3_range").getArrayValue(1));
  
  for(int i = 0 ; i < 6 ; i++){
    six_gemstone[i].set_trigger_segment1_speed(segment1_speed);
    six_gemstone[i].set_trigger_segment2_speed(segment2_speed);
    six_gemstone[i].set_trigger_segment3_speed(segment3_speed);
    six_gemstone[i].set_clean_speed(clean_speed);
    six_gemstone[i].set_trigger_segment3_range(alpha_min , alpha_max);
  }
  
  set_color_1();
  set_color_2();
  set_color_3();
  set_color_4();
  set_color_5();
  set_color_6();
}

public void keyPressed() {
  // default properties load/save key combinations are 
  // alt+shift+l to load properties
  // alt+shift+s to save properties
  if (key=='s') {
    cp5.saveProperties(("Marvel_light.properties"));
  } 
  else if (key=='l') {
    cp5.loadProperties(("Marvel_light.properties"));
  }
}
public void R_1(int value){
  set_color_1();
}
public void G_1(int value){
  set_color_1();
}
public void B_1(int value){
  set_color_1();
}

public void set_color_1(){
  int r = PApplet.parseInt(cp5.get("R_1").getValue());
  int g = PApplet.parseInt(cp5.get("G_1").getValue());
  int b = PApplet.parseInt(cp5.get("B_1").getValue());
  
  six_gemstone[0].set_color(color(r,g,b));
}

public void R_2(int value){
  set_color_2();
}
public void G_2(int value){
  set_color_2();
}
public void B_2(int value){
  set_color_2();
}

public void set_color_2(){
  int r = PApplet.parseInt(cp5.get("R_2").getValue());
  int g = PApplet.parseInt(cp5.get("G_2").getValue());
  int b = PApplet.parseInt(cp5.get("B_2").getValue());
  
  six_gemstone[1].set_color(color(r,g,b));
}

public void R_3(int value){
  set_color_3();
}
public void G_3(int value){
  set_color_3();
}
public void B_3(int value){
  set_color_3();
}

public void set_color_3(){
  int r = PApplet.parseInt(cp5.get("R_3").getValue());
  int g = PApplet.parseInt(cp5.get("G_3").getValue());
  int b = PApplet.parseInt(cp5.get("B_3").getValue());
  
  six_gemstone[2].set_color(color(r,g,b));
}

public void R_4(int value){
  set_color_4();
}
public void G_4(int value){
  set_color_4();
}
public void B_4(int value){
  set_color_4();
}

public void set_color_4(){
  int r = PApplet.parseInt(cp5.get("R_4").getValue());
  int g = PApplet.parseInt(cp5.get("G_4").getValue());
  int b = PApplet.parseInt(cp5.get("B_4").getValue());
  
  six_gemstone[3].set_color(color(r,g,b));
}

public void R_5(int value){
  set_color_5();
}
public void G_5(int value){
  set_color_5();
}
public void B_5(int value){
  set_color_5();
}

public void set_color_5(){
  int r = PApplet.parseInt(cp5.get("R_5").getValue());
  int g = PApplet.parseInt(cp5.get("G_5").getValue());
  int b = PApplet.parseInt(cp5.get("B_5").getValue());
  
  six_gemstone[4].set_color(color(r,g,b));
}

public void R_6(int value){
  set_color_6();
}
public void G_6(int value){
  set_color_6();
}
public void B_6(int value){
  set_color_6();
}

public void set_color_6(){
  int r = PApplet.parseInt(cp5.get("R_6").getValue());
  int g = PApplet.parseInt(cp5.get("G_6").getValue());
  int b = PApplet.parseInt(cp5.get("B_6").getValue());
  
  six_gemstone[5].set_color(color(r,g,b));
}
class PowerStream{
  
  int first_segment_size = 80;
  int second_segment_szie = 100;
  int third_segment_size = 60;
  int segment_color;
  
  int mode = 0;
  
  
  float light_point = 0;
  float current_alpha = 0;
  int light_point_length = 30;
  
  // Tigger mode initialization
  int []tigger_segment1_sinMap = new int[light_point_length];
  int []tigger_segment2_sinMap = new int[light_point_length];
  int []tigger_segment3_sinMap = new int[256];
  int tiggerMode_level = 0;
  float trigger_segment1_speed = 1;
  float trigger_segment2_speed = 1;
  float trigger_segment3_speed = 1;
  int trigger_segment3_min = 0;
  int trigger_segment3_max = 255;
  float trigger_segment3_temp_alpha = 0;
  
  
  // Clean mode initialization
  int []breath_sinMap = new int[256];
  int []clean_sinMap = new int[light_point_length];
  int cleanMode_level = 0;
  float clean_speed = 1;
  
  //Alpha
  float alpha = 1;
  boolean interrupt_alpha = false;
  float fadeout_speed = 10;
  
  PowerStream(int _first_segment_size, int _second_segment_szie, int _third_segment_size, int _segment_color){
    
    first_segment_size = _first_segment_size;
    second_segment_szie = _second_segment_szie;
    third_segment_size = _third_segment_size;
    
    segment_color = _segment_color;
    
    // Sin map preset
    for(int i = 0 ; i <light_point_length ; i++){
      tigger_segment1_sinMap[i] = (int)map(pow(sin( TWO_PI/(light_point_length*2) *(i+1))+1,4),0,16,0,255);
    }
    
    for(int i = 0 ; i <light_point_length ; i++){
      tigger_segment2_sinMap[i] = (int)map(sin( TWO_PI/(light_point_length*2) *(i+1) - TWO_PI/4),-1,1,0,255);
      clean_sinMap[i] = tigger_segment2_sinMap[i];
    }
    
    for(int i = 0 ; i < 256 ; i++){
      tigger_segment3_sinMap[i] =  (int)map(sin( TWO_PI/256 * (i) + PI/2),-1,1,trigger_segment3_min,trigger_segment3_max);
    }
    //
    
  }
  
  public void reset(){
    light_point = 0;
    current_alpha = 0;
    tiggerMode_level = 0;
    cleanMode_level = 0;
  }
  
  public void set_color(int _segment_color){
    segment_color = _segment_color;
  }
  
  public void set_trigger_segment1_speed(int level){
    trigger_segment1_speed = level + 1;
  }
  
  public void set_trigger_segment2_speed(int level){
    trigger_segment2_speed = level + 1;
  }
  
  public void set_trigger_segment3_speed(int level){
    trigger_segment3_speed = level*0.5f + 0.5f;
  }
  
  public void set_trigger_segment3_range(int min , int max){
    
    trigger_segment3_min = min;
    trigger_segment3_max = max;
  }
  
  public void set_clean_speed(int level){
    clean_speed = level*0.5f + 0.5f;
  }
  
  public void set_fadeout_speed(int level){
    fadeout_speed = level*10 + 10;
  }
  public void set_alpha(float a){
    alpha = a;
  }
  
  public float get_alpha(){
    return alpha;
  }
  
  public void setMode(int _mode){
    // 0: black , 1:tigger,  2:close
    
    if(mode == 0 && _mode == 1){
      mode = _mode;
      light_point = 0;
      current_alpha = 0;
      tiggerMode_level = 0;
      cleanMode_level = 0;
    }
    
    if(mode == 1 && _mode == 2 ){
      
      if(tiggerMode_level == 2){
        mode = _mode;
        light_point = 0;
        current_alpha = 0;
        tiggerMode_level = 0;
        cleanMode_level = 0;
      }else{
        interrupt_alpha = true;
      }
    }
    
    if(mode == 2 && _mode == 0 && cleanMode_level == 1){
      
      mode = _mode;
      light_point = 0;
      current_alpha = 0;
      tiggerMode_level = 0;
      cleanMode_level = 0;
    }else if(mode == 1 && _mode == 0){
      
      interrupt_alpha = false;
      mode = _mode;
      light_point = 0;
      current_alpha = 0;
      tiggerMode_level = 0;
      cleanMode_level = 0;
      alpha = 255;
    }
    
  }
  
  public int getMode(){
    return mode;
  }
  
  public void draw(){
    
    switch(mode){
      case 0:
        blackMode();break;
      case 1:
        tiggerMode();break;
      case 2:
        cleanMode();break;
      
    }
    
    //translate(0,pixel_size);
    translate(pixel_size,0);
  }
  
  public void blackMode(){

  }
  
  public void tiggerMode(){
    
    if(interrupt_alpha)
       cleanMode_alpha();
    
    switch(tiggerMode_level){
      
      case 0:
        pushMatrix(); 
            
          for(int i = 0 ; i < first_segment_size + second_segment_szie + third_segment_size ; i++){
            pushStyle();
            if( i > light_point - light_point_length && i < light_point)
              fill(segment_color , PApplet.parseInt(tigger_segment1_sinMap[PApplet.parseInt(light_point) - i] * alpha));
            else
              fill(0);
              
            rect(0,i*pixel_size,pixel_size,pixel_size);
            //rect(i*pixel_size,0,pixel_size,pixel_size);
            popStyle();
          }
          
        popMatrix();
        
        if(light_point >= first_segment_size + second_segment_szie + third_segment_size + light_point_length){
          tiggerMode_level++;
          light_point = first_segment_size + second_segment_szie + third_segment_size - 1;
        }else
          light_point+= trigger_segment1_speed;
        break;
      case 1:
            
        for(int i = 0 ; i < first_segment_size + second_segment_szie + third_segment_size ; i++){
          
          pushStyle();
          if( i > light_point && i < (light_point + light_point_length))
            fill(segment_color , PApplet.parseInt(tigger_segment2_sinMap[i-PApplet.parseInt(light_point)] * alpha));
          else if( i >= (light_point + light_point_length))
            fill(segment_color , PApplet.parseInt(255 * alpha));
          else
            fill(0);
          rect(0,i*pixel_size,pixel_size,pixel_size);
          //rect(i*pixel_size,0,pixel_size,pixel_size);
          popStyle();
        }
        if(light_point < -light_point_length)
          tiggerMode_level++;
        else
          light_point-= trigger_segment2_speed;
        break;
      case 2:
      
        pushMatrix();
          for(int i = 0 ; i < first_segment_size + second_segment_szie + third_segment_size ; i++){
            pushStyle();
           
            //fill( segment_color , tigger_segment3_sinMap[constrain(int(current_alpha),0,255)]);
            fill( segment_color ,  PApplet.parseInt(map(sin( TWO_PI/256 * (constrain(PApplet.parseInt(current_alpha),0,255)) + PI/2),-1,1,trigger_segment3_min,trigger_segment3_max) * alpha));
            rect(0,i*pixel_size,pixel_size,pixel_size);
            //rect(i*pixel_size,0,pixel_size,pixel_size);
            popStyle();
          }
      
          if(current_alpha >= 255)
            current_alpha = 0;
          else
            current_alpha+= trigger_segment3_speed;
            
          trigger_segment3_temp_alpha = current_alpha;
        popMatrix();
        
    }

    
  }
  
  public void cleanMode(){
    
    switch(cleanMode_level){
      
      case 0:
        pushMatrix();
          for(int i = 0 ; i < first_segment_size + second_segment_szie + third_segment_size ; i++){
            pushStyle();
           
            //fill( segment_color , tigger_segment3_sinMap[constrain(int(current_alpha),0,255)]);
            fill( segment_color ,  PApplet.parseInt(map(sin( TWO_PI/256 * (constrain(PApplet.parseInt(trigger_segment3_temp_alpha),0,255)) + PI/2),-1,1,trigger_segment3_min,trigger_segment3_max) * alpha));
            rect(0,i*pixel_size,pixel_size,pixel_size);
            //rect(i*pixel_size,0,pixel_size,pixel_size);
            popStyle();
          }
            
          if(trigger_segment3_temp_alpha < 255)
            trigger_segment3_temp_alpha+= trigger_segment3_speed;
            
          if(trigger_segment3_temp_alpha >= 255){
            trigger_segment3_temp_alpha = 0;
            cleanMode_level++;
          }
          
          
        popMatrix();
        
        break;
        
      case 1:
        int temp = first_segment_size + second_segment_szie + third_segment_size - PApplet.parseInt(light_point);
        pushMatrix();
        
          for(int i = first_segment_size + second_segment_szie + third_segment_size-1 ; i >=0 ; i--){
            pushStyle();
            if( i > temp  && i < temp + light_point_length)
              fill(segment_color , PApplet.parseInt(255- tigger_segment2_sinMap[i-temp] * alpha));
            else if( i > temp)
              fill(0);
            else
              fill(segment_color , PApplet.parseInt(255 * alpha));
            rect(0,i*pixel_size,pixel_size,pixel_size);
            //rect(i*pixel_size,0,pixel_size,pixel_size);
            popStyle();
          }
          if(light_point <= first_segment_size + second_segment_szie + third_segment_size + light_point_length)
            light_point += clean_speed;
          else
            setMode(0);

        popMatrix();
        break;
    }
    
  }
  
  public void cleanMode_alpha(){
    
    if(get_alpha() <= 0 )
      setMode(0);
    
    float current_alpha = get_alpha();
    set_alpha( constrain( current_alpha - (1.0f/255*fadeout_speed) , 0 , 1) );
    
  }
  
}
  public void settings() {  size(800,660); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Marvel_6Light" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
