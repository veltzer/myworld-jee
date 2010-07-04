package meta.pdata.data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.Temporal;

@Entity
public class Person implements Serializable {
	public Person() {
	}

	private Long id;

	@Id
	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	private String honorific;

	public String getHonorific() {
		return honorific;
	}

	public void setHonorific(String honorific) {
		this.honorific = honorific;
	}

	private String firstname;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	private String surname;

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	private String initials;

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	private Date birthDate;

    @Temporal(javax.persistence.TemporalType.DATE)
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	private boolean dead;

	public boolean getDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	private Date deathDate;

    @Temporal(javax.persistence.TemporalType.DATE)
	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}