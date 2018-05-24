import oscP5.*;
import netP5.*;
import controlP5.*;


OscP5 oscP5;
NetAddress other;
ControlP5 cp5;

void setup(){
  size(400,400); 
  
  oscP5 = new OscP5(this,10000);
  
  other = new NetAddress("127.0.0.1",12000);
  
  cp5 = new ControlP5(this);
  //cp5.addButton("Breath")
  //   .setValue(0)
  //   .setPosition(100,100)
  //   .setSize(200,19)
  //   ;
  
  //// and add another 2 buttons
  //cp5.addButton("Trigger")
  //   .setValue(100)
  //   .setPosition(100,120)
  //   .setSize(200,19)
  //   ;
     
  //cp5.addButton("Clean")
  //   .setPosition(100,140)
  //   .setSize(200,19)
  //   .setValue(0)
  //   ;
  
  ButtonBar b0 = cp5.addButtonBar("bar_0")
     .setPosition(0, 0)
     .setSize(400, 20)
     .addItems(split("black tigger clean "," "))
     ;
     
  ButtonBar b1 = cp5.addButtonBar("bar_1")
     .setPosition(0, 50)
     .setSize(400, 20)
     .addItems(split("black tigger clean "," "))
     ;
  ButtonBar b2 = cp5.addButtonBar("bar_2")
     .setPosition(0, 100)
     .setSize(400, 20)
     .addItems(split("black tigger clean "," "))
     ;
     
  ButtonBar b3 = cp5.addButtonBar("bar_3")
     .setPosition(0, 150)
     .setSize(400, 20)
     .addItems(split("black tigger clean "," "))
     ;
     
  ButtonBar b4 = cp5.addButtonBar("bar_4")
   .setPosition(0, 200)
     .setSize(400, 20)
     .addItems(split("breath tigger clean "," "))
     ;
     
  ButtonBar b5 = cp5.addButtonBar("bar_5")
     .setPosition(0, 250)
     .setSize(400, 20)
     .addItems(split("breath tigger clean "," "))
     ;
}

int currentTime = 0;

void draw(){
  
}

void bar_0(int n ){
  
  OscMessage msg = selectMode(n);
  msg.add(0);
  oscP5.send(msg,other);
}

void bar_1(int n ){
  OscMessage msg = selectMode(n);
  msg.add(1);
  oscP5.send(msg,other);
}

void bar_2(int n ){
  OscMessage msg = selectMode(n);
  msg.add(2);
  oscP5.send(msg,other);
}

void bar_3(int n ){
  OscMessage msg = selectMode(n);
  msg.add(3);
  oscP5.send(msg,other);
}

void bar_4(int n ){
  OscMessage msg = selectMode(n);
  msg.add(4);
  oscP5.send(msg,other);
}

void bar_5(int n ){
  OscMessage msg = selectMode(n);
  msg.add(5);
  oscP5.send(msg,other);
}

OscMessage selectMode(int n){
  OscMessage msg =  new OscMessage("/black");
  switch(n){
    case 0:
      msg = new OscMessage("/black"); break;
    case 1:
      msg = new OscMessage("/trigger"); break;
    case 2:
      msg = new OscMessage("/clean"); break;
  }
  
  return msg;
}