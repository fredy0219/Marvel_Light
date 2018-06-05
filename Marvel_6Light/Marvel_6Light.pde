import oscP5.*;
import netP5.*;

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

import controlP5.*;
import java.util.*;
ControlP5 cp5;

void setup(){
  //size(426,18);
  
  //size(660,30);
  size(800,660);
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

void set_black(int id){
  print("breath" + id);
  six_gemstone[id].setMode(0);
}

void set_trigger(int id){
  print("trigger" + id);
  six_gemstone[id].setMode(1);
}

void set_clean(int id){
  print("clean" + id);
  six_gemstone[id].setMode(2);
}

// CONTRAL PANEL 
void segment1_speed(int level){
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].set_trigger_segment1_speed(level);
}

void segment2_speed(int level){
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].set_trigger_segment2_speed(level);
}

void segment3_speed(int level){
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].set_trigger_segment3_speed(level);
}

void controlEvent(ControlEvent theControlEvent) {
  if(theControlEvent.isFrom("segment3_range")) {
    int colorMin = int(theControlEvent.getController().getArrayValue(0));
    int colorMax = int(theControlEvent.getController().getArrayValue(1));
    
    for(int i = 0 ; i < 6 ; i++)
      six_gemstone[i].set_trigger_segment3_range(colorMin , colorMax);
  }
}



void clean_speed(int level){
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].set_clean_speed(level);
}

void fadeout_speed(int level){
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].set_fadeout_speed(level);
}
void alpha(float a){
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].set_alpha(a);
}

void draw(){
  background(0); 
  noStroke();
  
  
  pushMatrix();
  translate(10,0);
  for(int i = 0 ; i < 6 ; i++)
    six_gemstone[i].draw();
  popMatrix();
}

void init(){
  
  int segment1_speed = int(cp5.get("segment1_speed").getValue());
  int segment2_speed = int(cp5.get("segment2_speed").getValue());
  int segment3_speed = int(cp5.get("segment3_speed").getValue());
  int clean_speed = int(cp5.get("clean_speed").getValue());
  int alpha_min = int(cp5.get("segment3_range").getArrayValue(0));
  int alpha_max = int(cp5.get("segment3_range").getArrayValue(1));
  
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

void keyPressed() {
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