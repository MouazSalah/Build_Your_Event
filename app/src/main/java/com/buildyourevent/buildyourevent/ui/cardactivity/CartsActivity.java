package com.buildyourevent.buildyourevent.ui.cardactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.auth.login.UserData;
import com.buildyourevent.buildyourevent.model.data.carts.CartDataItem;
import com.buildyourevent.buildyourevent.model.data.carts.CartResponse;
import com.buildyourevent.buildyourevent.model.data.order.OrderRequest;
import com.buildyourevent.buildyourevent.model.data.order.OrderResponse;
import com.buildyourevent.buildyourevent.model.data.removefromcart.RemoveCartRequest;
import com.buildyourevent.buildyourevent.model.data.removefromcart.RemoveCartResponse;
import com.buildyourevent.buildyourevent.ui.auth.LoginActivity;
import com.buildyourevent.buildyourevent.ui.home.HomeActivity;
import com.buildyourevent.buildyourevent.ui.products.ProductInfoActivity;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartsActivity extends AppCompatActivity implements CartAdapter.onCartItemListener
{
    @BindView(R.id.carts_recyclerview) RecyclerView cartsRecyclerView;
    @BindView(R.id.emptycart_layout) LinearLayout emptyCardLayout;
    @BindView(R.id.notlogin_layout) LinearLayout notLoginLayout;
    @BindView(R.id.carts_progressBar) ProgressBar progressBar;
    @BindView(R.id.cartscount_textview) TextView cartsCounTextView;

    SharedPrefMethods prefMethods;
    UserData userData;
    UserViewModel viewModel;

    CartAdapter cartAdapter;
    List<CartDataItem> cartsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        prefMethods = new SharedPrefMethods(this);
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carts);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userData = prefMethods.getUserData();

        if (userData == null)
        {
            notLoginLayout.setVisibility(View.VISIBLE);
            emptyCardLayout.setVisibility(View.GONE);
            cartsRecyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            cartsCounTextView.setText("0");
        }
        else
        {
            getUserCarts();
        }
    }

    private void getUserCarts()
    {
        viewModel.getAllCarts(userData.getId(), userData.getToken()).observe(this, new Observer<CartResponse>() {
            @Override
            public void onChanged(CartResponse cartResponse)
            {
                if (cartResponse.getStatus() == 200)
                {
                    cartsList = (ArrayList<CartDataItem>) cartResponse.getData();
                    notLoginLayout.setVisibility(View.GONE);
                    emptyCardLayout.setVisibility(View.GONE);
                    cartsRecyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    buildRecyclerView();
                    cartsCounTextView.setText("" + cartsList.size());
                }
                else
                {
                    notLoginLayout.setVisibility(View.GONE);
                    emptyCardLayout.setVisibility(View.VISIBLE);
                    cartsRecyclerView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    cartsCounTextView.setText("0");
                }
            }
        });
    }

    private void buildRecyclerView()
    {
        cartsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        cartsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //cartsRecyclerView.setLayoutManager(mLayoutManager);

        Log.d(Codes.APP_TAGS, "cards size: " + cartsList);
         cartAdapter = new CartAdapter(this, cartsList, this);
        cartAdapter.notifyDataSetChanged();

        cartsRecyclerView.setAdapter(cartAdapter);
    }

    @OnClick(R.id.ordercarts_button)
    void orderCarts(View v)
    {
        progressBar.setVisibility(View.VISIBLE);
        double price = 0;
        for (int i = 0; i < cartsList.size(); i ++)
        {
            price = price + i;
        }

        OrderRequest orderRequest = new OrderRequest(userData.getId(), userData.getToken(), price);
        viewModel.confirmOrder(orderRequest).observe(this, new Observer<OrderResponse>()
        {
            @Override
            public void onChanged(OrderResponse orderResponse)
            {
                if (orderResponse.getStatus() == 200)
                {
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra(Codes.ORDER_REQUEST , orderRequest);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CartsActivity.this, "Try Again...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), CartsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @OnClick(R.id.startshopping_button)
    void startShopping(View v)
    {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.loginnow_button)
    void loginNowTask(View v)
    {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemCartClick(int position)
    {
        String [] items = {"Delete" , "Edit"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a language");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (which == 0)
                {
                    removeCartItem(position);
                }
                if (which == 1)
                {
                    prefMethods.saveProductId(cartsList.get(position).getProductId());

                    startActivity(new Intent(getApplicationContext(), ProductInfoActivity.class));
                    finish();
                }
            }
        });
        builder.show();
    }

    private void removeCartItem(int position)
    {
        progressBar.setVisibility(View.VISIBLE);
        RemoveCartRequest removeCartRequest = new RemoveCartRequest(userData.getId(), userData.getToken(), cartsList.get(position).getCartId());
        viewModel.removeFromCart(removeCartRequest).observe(this, new Observer<RemoveCartResponse>() {
            @Override
            public void onChanged(RemoveCartResponse removeCartResponse) {
                if (removeCartResponse.getStatus() == 200)
                {
                    progressBar.setVisibility(View.GONE);
                    cartAdapter.notifyDataSetChanged();
                    /*Fragment currentFragment = new ProductsFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.nav_host_fragment, currentFragment);
                    ft.commit();*/
                    startActivity(new Intent(getApplicationContext(), CartsActivity.class));
                    finish();
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CartsActivity.this, "Try Later...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}
