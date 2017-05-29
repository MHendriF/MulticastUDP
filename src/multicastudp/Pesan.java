package multicastudp;

public class Pesan {
    public static String _ListPesan[] = new String [50];
    public static String isipesan, tujuan;
    private String tes;
    public int hop;
    int i;
    
    Pesan(){
        for(i=0; i<_ListPesan.length; i++){
            _ListPesan[i] = null;  
        }
    }
    
    void addPesan(int i, String pesan){
        this._ListPesan[i]= pesan; 
    }

    String cekPesan(int i){
        return _ListPesan[i];
    }
    
}
