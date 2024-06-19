package pack.order;

import java.util.Hashtable;

public class CartMgr {
	private Hashtable<String, OrderBean> hCart = new Hashtable<String, OrderBean>();
	
	public void addCart(OrderBean obean) {
		String product_no= obean.getProduct_no();
		int quantity = Integer.parseInt(obean.getQuantity());
		
		
		if(quantity > 0) {
			//동일 상품 주문인 경우에는 주문 수량만 증가
			if(hCart.containsKey(product_no)) { //이미 1회 이상 주문된 상품인 경우
				OrderBean temp = hCart.get(product_no); //해당 상품을 꺼냄
				quantity += Integer.parseInt(temp.getQuantity());
				temp.setQuantity(Integer.toString(quantity));
				hCart.put(product_no, temp);
				
			}else {
				
				hCart.put(product_no, obean);  //cart에 담기는 최초의 상품(상품의 종류는 다름)
			}
		}
		
	}
	public Hashtable<String, OrderBean> getCartlist(){
		return hCart;
		
	}
	public void updateCart(OrderBean obean) {
		String product_no = obean.getProduct_no();
		hCart.put(product_no, obean);
	}
	public void deleteCart(OrderBean obean) {
		String product_no = obean.getProduct_no();
		hCart.remove(product_no);
	}
}
