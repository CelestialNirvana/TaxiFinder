public class TaxiSearchModel {

     String Date;
     String ServiceNumber;
     Integer Number;
     Integer OffService;

    public TaxiSearchModel(Integer Number,String ServiceNumber, String Date, Integer OffService ){
      
        this.Number = Number;
        this.ServiceNumber = ServiceNumber;
        this.Date = Date;
        this.OffService = OffService;
    }


    public Integer getNumber(){
        return Number;
    }
    public String getServiceNumber(){
        return ServiceNumber;
    }
    public String getDate(){
        return Date;
    }
    public Integer getOffService(){
        return OffService;
    }

    public void setNumber(Integer Number){
        this.Number = Number;
    }

    public void setServiceNumber(String ServiceNumber){
        this.ServiceNumber = ServiceNumber;
    }

    public void setDate(String Date){
        this.Date = Date;
    }

    public void setOffService(Integer OffService){
        this.OffService = OffService;
    }
    
}
