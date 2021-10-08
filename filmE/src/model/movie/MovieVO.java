package model.movie;

public class MovieVO {
	
	private String mpk;				//pk �帣 + nvl 
	private String title;			//��ȭ �̸�
	private String content;			//��ȭ ����
	private String mtype;			//��ȭ �帣
	private String mdate;			//��ȭ ������
	private String filename;		//��ȭ �̹���
	
	
	public String getMpk() {
		return mpk;
	}
	public void setMpk(String mpk) {
		this.mpk = mpk;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMtype() {
		return mtype;
	}
	public void setMtype(String mtype) {
		this.mtype = mtype;
	}
	public String getMdate() {
		return mdate;
	}
	public void setMdate(String mdate) {
		this.mdate = mdate;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Override
	public String toString() {
		return "MovieVO [mpk=" + mpk + ", title=" + title + ", content=" + content + ", mtype=" + mtype + ", mdate="
				+ mdate + ", filename=" + filename + "]";
	}
	
	

}
