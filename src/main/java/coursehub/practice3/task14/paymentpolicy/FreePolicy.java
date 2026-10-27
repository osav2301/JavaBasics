package coursehub.practice3.task14.paymentpolicy;

public class FreePolicy implements PaymentPolicy {
    @Override
    public double getPrice()
    {
        return 0;
    }

    @Override
    public String describe()
    {
        return "Бесплатно";
    }
}
