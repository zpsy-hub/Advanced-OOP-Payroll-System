package service;

public class PermissionWithStatus {
	private model.Permission permission;
	private boolean hasPermission;

	public PermissionWithStatus(model.Permission permission, boolean hasPermission) {
		this.permission = permission;
		this.hasPermission = hasPermission;
	}

	public model.Permission getPermission() {
		return permission;
	}

	public boolean hasPermission() {
		return hasPermission;
	}

	public void setHasPermission(boolean hasPermission) {
		this.hasPermission = hasPermission;
	}
}


