package com.foodorderapp.models.binding.user;

import com.foodorderapp.util.GeneralUtils;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Map;
@Getter
public class LocalUserBindingModel extends User {

    private static final long serialVersionUID = -2845160792248762779L;
    private Map<String, Object> attributes;
    private com.foodorderapp.models.entity.User user;

    public LocalUserBindingModel(
            final String userID,
            final String password,
            final boolean enabled,
            final boolean accountNonExpired,
            final boolean credentialsNonExpired,
            final boolean accountNonLocked,
            final Collection<? extends GrantedAuthority> authorities,
            final com.foodorderapp.models.entity.User user) {
        super(userID, password, enabled,
                accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        this.user = user;
    }

    public static LocalUserBindingModel create(
            com.foodorderapp.models.entity.User user,
            Map<String, Object> attributes) {
        LocalUserBindingModel localUser =
                new LocalUserBindingModel(
                        user.getEmail(),
                        user.getPassword(),
                        user.isEnabled(),
                        true,
                        true,
                        true,
                        GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()),
                        user);
        localUser.setAttributes(attributes);

        return localUser;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getName() {
        return this.user.getDisplayName();
    }

}
