package coursehub.practice3.task14.paymentpolicy;

public class FixedPolicy implements PaymentPolicy {
    private double price;

    public FixedPolicy(double price)
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
        return "Фиксированная цена: " + getPrice();
    }
}
