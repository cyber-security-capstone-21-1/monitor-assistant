package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service;

public class ChangeImgSrc {
    String html;

    public ChangeImgSrc(String original, int type) {
        String find="<img src=\"";
        if(type==1){
            if(original.contains(find)){
                this.html=original.replace(find,"<img src=\"https://www.dogdrip.net");
            }
            else{
                this.html=original;
            }
        }
        else if(type ==2){
            find+="//cdn";
            if(original.contains(find)){
                this.html=original.replace(find,"<img src=\"https://cdn");
            }
            else{
                this.html=original;
            }
        }
    }

    public String getHtml() {
        return html;
    }
}
