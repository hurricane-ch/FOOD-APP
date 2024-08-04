package com.foodorderapp.models.binding.auth;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.foodorderapp.validators.password.PasswordMatches;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PasswordMatches
public class SignUpRequestBindingModel {

    @NotEmpty
    private String displayName;

    @NotEmpty
    private String email;

    @Size(min = 5, message = "{Size.userDto.password}")
    private String password;

    @NotEmpty
    private String matchingPassword;

    public SignUpRequestBindingModel(
            String displayName,
            String email,
            String password) {
        this.displayName = displayName;
        this.email = email;
        this.password = password;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String providerUserID;
        private String displayName;
        private String email;
        private String password;

        public Builder addProviderUserID(final String userID) {
            this.providerUserID = userID;
            return this;
        }

        public Builder addDisplayName(final String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder addEmail(final String email) {
            this.email = email;
            return this;
        }

        public Builder addPassword(final String password) {
            this.password = password;
            return this;
        }

        public SignUpRequestBindingModel build() {
            return new SignUpRequestBindingModel(displayName, email, password);
        }
    }
}
