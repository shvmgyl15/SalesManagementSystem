package salesmanagement;

public enum OrderStatus {
	BLOC("Order Entry done"), REVD("Order checked by COPG"), CLRD(
			"Order cleared by COPG"), SCHD("Order scheduled by PPC"), SHIP(
			"Order Shipped by dispatch section"), INVG(
			"Invoice generated by accounts department"), MACI(
			"Machine installed by installation department"), PYMR(
			"Payment Received from customer");
	private String description;

	private OrderStatus(String d) {
		description = d;
	}

	public String getDescription() {
		return description;
	}
}