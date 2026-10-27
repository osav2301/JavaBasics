package coursehub.practice3.task14.paymentpolicy;

public class NewPaymentPolicy implements PaymentPolicy {
    private double price;

    public NewPaymentPolicy(double price)
    {
        this.price = price;
    }

    @Override
    public double getPrice()
    {
        return price;
    }

    @Override
    public String describe()
    {
        return "Образовательный кредит: " + getPrice();
    }
}
