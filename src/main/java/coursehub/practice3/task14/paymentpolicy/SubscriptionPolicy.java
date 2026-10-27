package coursehub.practice3.task14.paymentpolicy;

public class SubscriptionPolicy implements PaymentPolicy {
    private double price;

    public SubscriptionPolicy(double price)
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
        return "Ежемесечная цена: " + getPrice();
    }
}
