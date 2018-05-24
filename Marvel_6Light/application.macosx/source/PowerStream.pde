class PowerStream{
  
  int first_segment_size = 80;
  int second_segment_szie = 100;
  int third_segment_size = 60;
  color segment_color;
  
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
  
  PowerStream(int _first_segment_size, int _second_segment_szie, int _third_segment_size, color _segment_color){
    
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
  
  void reset(){
    light_point = 0;
    current_alpha = 0;
    tiggerMode_level = 0;
    cleanMode_level = 0;
  }
  
  void set_color(color _segment_color){
    segment_color = _segment_color;
  }
  
  void set_trigger_segment1_speed(int level){
    trigger_segment1_speed = level + 1;
  }
  
  void set_trigger_segment2_speed(int level){
    trigger_segment2_speed = level + 1;
  }
  
  void set_trigger_segment3_speed(int level){
    trigger_segment3_speed = level*0.5 + 0.5;
  }
  
  void set_trigger_segment3_range(int min , int max){
    
    trigger_segment3_min = min;
    trigger_segment3_max = max;
  }
  
  void set_clean_speed(int level){
    clean_speed = level*0.5 + 0.5;
  }
  
  void set_alpha(float a){
    alpha = a;
  }
  
  float get_alpha(){
    return alpha;
  }
  
  void setMode(int _mode){
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
  
  int getMode(){
    return mode;
  }
  
  void draw(){
    
    switch(mode){
      case 0:
        blackMode();break;
      case 1:
        tiggerMode();break;
      case 2:
        cleanMode();break;
      
    }
    
    translate(0,pixel_size);

  }
  
  void blackMode(){

  }
  
  void tiggerMode(){
    
    if(interrupt_alpha)
       cleanMode_alpha();
    
    switch(tiggerMode_level){
      
      case 0:
        pushMatrix(); 
            
          for(int i = 0 ; i < first_segment_size + second_segment_szie + third_segment_size ; i++){
            pushStyle();
            if( i > light_point - light_point_length && i < light_point)
              fill(segment_color , int(tigger_segment1_sinMap[int(light_point) - i] * alpha));
            else
              fill(0);
              
            //rect(0,i*pixel_size,pixel_size,pixel_size);
            rect(i*pixel_size,0,pixel_size,pixel_size);
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
            fill(segment_color , int(tigger_segment2_sinMap[i-int(light_point)] * alpha));
          else if( i >= (light_point + light_point_length))
            fill(segment_color , int(255 * alpha));
          else
            fill(0);
          //rect(0,i*pixel_size,pixel_size,pixel_size);
          rect(i*pixel_size,0,pixel_size,pixel_size);
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
            fill( segment_color ,  int(map(sin( TWO_PI/256 * (constrain(int(current_alpha),0,255)) + PI/2),-1,1,trigger_segment3_min,trigger_segment3_max) * alpha));
            //rect(0,i*pixel_size,pixel_size,pixel_size);
            rect(i*pixel_size,0,pixel_size,pixel_size);
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
  
  void cleanMode(){
    
    switch(cleanMode_level){
      
      case 0:
        pushMatrix();
          for(int i = 0 ; i < first_segment_size + second_segment_szie + third_segment_size ; i++){
            pushStyle();
           
            //fill( segment_color , tigger_segment3_sinMap[constrain(int(current_alpha),0,255)]);
            fill( segment_color ,  int(map(sin( TWO_PI/256 * (constrain(int(trigger_segment3_temp_alpha),0,255)) + PI/2),-1,1,trigger_segment3_min,trigger_segment3_max) * alpha));
            //rect(0,i*pixel_size,pixel_size,pixel_size);
            rect(i*pixel_size,0,pixel_size,pixel_size);
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
        int temp = first_segment_size + second_segment_szie + third_segment_size - int(light_point);
        pushMatrix();
        
          for(int i = first_segment_size + second_segment_szie + third_segment_size-1 ; i >=0 ; i--){
            pushStyle();
            if( i > temp  && i < temp + light_point_length)
              fill(segment_color , int(255- tigger_segment2_sinMap[i-temp] * alpha));
            else if( i > temp)
              fill(0);
            else
              fill(segment_color , int(255 * alpha));
            rect(i*pixel_size,0,pixel_size,pixel_size);
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
  
  void cleanMode_alpha(){
    
    if(get_alpha() <= 0 )
      setMode(0);
    
    float current_alpha = get_alpha();
    set_alpha( constrain( current_alpha - (1.0/255*clean_speed) , 0 , 1) );
    
  }
  
}