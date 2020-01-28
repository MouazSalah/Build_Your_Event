package com.buildyourevent.buildyourevent.ui.cardactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.buildyourevent.buildyourevent.ui.products.SubCategoryAdapter;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        prefMethods = new SharedPrefMethods(this);

        prefMethods = new SharedPrefMethods(this);
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
        cartsRecyclerView.setLayoutManager(mLayoutManager);

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
        viewModel.confirmOrder(orderRequest).observe(this, new Observer<OrderResponse>() {
            @Override
            public void onChanged(OrderResponse orderResponse) {
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
        new AlertDialog.Builder(this)
                .setTitle("Deleting...")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        removeCartItem(position);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
                    Intent intent = new Intent(getApplicationContext(), CartsActivity.class);
                    startActivity(intent);
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
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
