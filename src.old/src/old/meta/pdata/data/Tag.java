package meta.pdata.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.IndexColumn;

@Entity
public class Tag implements Serializable {
	public Tag() {
	}

	private Long id;

	@Id
	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	private String name;

	@Column(unique = true, nullable = false)
	@IndexColumn(name = "name_idx")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
