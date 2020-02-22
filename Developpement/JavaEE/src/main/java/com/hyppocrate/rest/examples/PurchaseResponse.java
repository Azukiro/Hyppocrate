package com.hyppocrate.rest.examples;


public class PurchaseResponse {
    private boolean accepted;
    private String reason;

    public PurchaseResponse() {
    }

    public PurchaseResponse(final boolean accepted, final String reason) {
        this.accepted = accepted;
        this.reason = reason;
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public void setAccepted(final boolean accepted) {
        this.accepted = accepted;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        final PurchaseResponse that = (PurchaseResponse) o;

        if (this.accepted != that.accepted) return false;
        return this.reason.equals(that.reason);
    }

    @Override
    public int hashCode() {
        int result = (this.accepted ? 1 : 0);
        result = 31 * result + this.reason.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PurchaseResponse{" +
                "accepted=" + this.accepted +
                ", reason='" + this.reason + '\'' +
                '}';
    }
}
