package meta.pdata.data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.Temporal;

@Entity
public class Done implements Serializable {
	public Done() {
		dateDone = new Date();
	}

	private Long id;

	@Id
	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	private Date dateDone;

    @Temporal(javax.persistence.TemporalType.DATE)
	public Date getDateDone() {
		return dateDone;
	}

	public void setDateDone(Date date) {
		this.dateDone = date;
	}

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
