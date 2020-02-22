package com.hyppocrate.rest.examples;

public class Purchase {
    private String email;
    private String first_name;
    private String last_name;
    private int amount;

    public Purchase() {
    }

    public Purchase(final String email, final String first_name, final String last_name, final int amount) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.amount = amount;
    }
    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(final String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(final String last_name) {
        this.last_name = last_name;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(final int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        final Purchase purchase = (Purchase) o;

        if (this.amount != purchase.amount) return false;
        if (!this.email.equals(purchase.email)) return false;
        if (!this.first_name.equals(purchase.first_name)) return false;
        return this.last_name.equals(purchase.last_name);
    }

    @Override
    public int hashCode() {
        int result = this.email.hashCode();
        result = 31 * result + this.first_name.hashCode();
        result = 31 * result + this.last_name.hashCode();
        result = 31 * result + this.amount;
        return result;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "email='" + this.email + '\'' +
                ", first_name='" + this.first_name + '\'' +
                ", last_name='" + this.last_name + '\'' +
                ", amount=" + this.amount +
                '}';
    }
}
