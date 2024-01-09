package br.com.erudio.data.vo.v1.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TokenVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private boolean authenticated;
	private Date created;
	private Date expiration;
	private String accesToken;
	private String refreshToken;
	
	public TokenVO(String username, 
			boolean authenticated, 
			Date created, 
			Date expiration, 
			String accessToken,
			String refreshToken2) {
		
		this.username = username;
		this.authenticated = authenticated;
		this.created = created;
		this.expiration = expiration;
		this.accesToken = accessToken;
		this.refreshToken = refreshToken2;
	}
	public String getUsername() {
		return username;
	}
	public boolean isAuthenticated() {
		return authenticated;
	}
	public Date getCreated() {
		return created;
	}
	public Date getExpiration() {
		return expiration;
	}
	public String getAccesToken() {
		return accesToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	public void setAccesToken(String accesToken) {
		this.accesToken = accesToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	@Override
	public int hashCode() {
		return Objects.hash(accesToken, authenticated, created, expiration, refreshToken, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenVO other = (TokenVO) obj;
		return Objects.equals(accesToken, other.accesToken) && authenticated == other.authenticated
				&& Objects.equals(created, other.created) && Objects.equals(expiration, other.expiration)
				&& Objects.equals(refreshToken, other.refreshToken) && Objects.equals(username, other.username);
	}
	
	
}
