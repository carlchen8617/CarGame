package au.edu.swn.aj.student.chen.application;

public class gapCalcu {
	
	int CtrlSet;
	Long stoptime;
	Long laststarttime;
	Long laststoptime;
	int currentGapValue;
	int StopMap;
	
public gapCalcu(){
		
		
		
	}
	
	public gapCalcu(int CtrlSet,long laststoptime,long laststarttime,long stoptime, int currentGapValue,int StopMap){
		
		this.CtrlSet=CtrlSet;
		this.stoptime=stoptime;
		this.currentGapValue=currentGapValue;
		this.StopMap=StopMap;
		this.laststoptime=laststoptime;
		this.laststarttime=laststarttime;
		
	}
	
	public int getStopTime(int CtrlSet,long laststoptime,long laststarttime,long stoptime, int currentGapValue,int StopMap){
		int stopT=currentGapValue;
		
		if(CtrlSet==2){
			if(StopMap==0){
				
				return stopT;
			}
			else{
				   if(currentGapValue >3 ){
					   
					   if(StopMap==1){
							  stopT=(int) (currentGapValue-(laststarttime-laststoptime)+(stoptime-laststarttime)-(3600+1200*Math.pow(.85, StopMap)));
						  }
						  else
						  {
							  stopT=(int) (currentGapValue-(laststarttime-laststoptime)+(stoptime-laststarttime)+1200*Math.pow(0.85, StopMap));
						  }
					   
				   }
				 
				
				System.out.println("calcu: "+"Last Stop "+laststoptime +"  lastStart "+laststarttime +"  Stop "+ stoptime +"  will STOP "+ stopT);
				return stopT;
			}
		}
		else{
			 return stopT;
		}
		
		
	}
	
	public int getStartTime(int CtrlSet,Long stoptime,Long laststoptime, int currentGapValue){
		int startT=currentGapValue;
		
		return startT;
		
	}
	
	public Long getgapTime(int CtrlSet,Long stoptime,Long laststoptime, Long currentGapValue){
		Long gapT=(long)0;
		
		if(CtrlSet==1){ //1st and 2nd car
			
			
		}
		
		
		
		return gapT;
		
		
		
	}

}
