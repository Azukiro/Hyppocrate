package com.hyppocrate.rest.examples;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "Members", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @NotEmpty
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotNull
    @Digits(fraction = 0, integer = 12)
    private Integer amount = 0;

    public User() {
    }

    public User(final String email, final Integer amount) {
        this.email = email;
        this.amount = amount;
    }

    public void increaseAmount(final int amount) {
        this.amount += amount;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        final User user = (User) o;

        if (!this.amount.equals(user.amount)) return false;
        return this.email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = this.email.hashCode();
        result = 31 * result + this.amount.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Member{" +
                "email='" + this.email + '\'' +
                ", amount='" + this.amount + '\'' +
                '}';
    }
}