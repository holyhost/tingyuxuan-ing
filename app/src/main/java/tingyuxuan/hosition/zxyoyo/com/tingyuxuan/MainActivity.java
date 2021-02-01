package tingyuxuan.hosition.zxyoyo.com.tingyuxuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.base.BaseActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.DbUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.utils.ToastUtil;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.LoginActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.PersonActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.StartActivity;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.design.CircleImageView;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment.BorrowFragment;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment.FavorityFragment;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment.HistoryFragment;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment.HomeFragment;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment.ManagerFragment;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment.ReadRecordFragment;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.frgment.SettingFragment;
import tingyuxuan.hosition.zxyoyo.com.tingyuxuan.view.WalletActivity;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    private int userLevel;
    private HomeFragment homeFragment;
    private Fragment[] fragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        //初始化主界面fragment
        initFragment();
        //初始化侧滑菜单
        initNavigationView();
    }

    @Override
    public void initToolBar() {

    }

    private void initFragment() {

        homeFragment = HomeFragment.newInstance();
        ManagerFragment managerFragment = ManagerFragment.newInstance();
        FavorityFragment favorityFragment = FavorityFragment.newInstance();
        SettingFragment settingFragment = SettingFragment.newInstance();
        ReadRecordFragment historyFragment = ReadRecordFragment.newInstance();
        BorrowFragment borrowFragment = BorrowFragment.newInstance();
        fragments = new Fragment[]{
                homeFragment,
                borrowFragment,
                favorityFragment,
                historyFragment,
                settingFragment,
                managerFragment
        };
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, homeFragment)
                .show(homeFragment).commit();
    }

    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);
        CircleImageView mUserAvatarView = (CircleImageView) headerView.findViewById(R.id.user_avatar_view);
        TextView mUserName = (TextView) headerView.findViewById(R.id.user_name);
        TextView mUserLevel = (TextView) headerView.findViewById(R.id.user_level);
        TextView logo = (TextView) headerView.findViewById(R.id.tv_pink);
        ImageView iv_wallet = headerView.findViewById(R.id.iv_head_noftiy);
        String name = getSharedPreferences("user", MODE_PRIVATE).getString("username", "听雨喧");
        userLevel = DbUtil.getUserLevel(this, name);
        iv_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this,WalletActivity.class));
            }
        });
        mUserLevel.setText("LV"+ userLevel);
        //设置名字
        mUserName.setText(name);
        mUserAvatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PersonActivity.class));
            }
        });
        switch (userLevel){
            case 1:
                logo.setText("普通用户");
                break;
            case 2:
                logo.setText("管理员");
                break;
            case 3:
                logo.setText("中级管理员");
                break;
            case 4:
                logo.setText("高级管理员");
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                logo.setText("至尊管理员");
                break;
            case 9:
                break;

        }

    }


    public void test(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_exit:
                //切换帐号
                getSharedPreferences("user", MODE_PRIVATE).edit().putString("username","").commit();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
                //主页
            case R.id.item_home:
                toggleDrawer();
                boolean update = getSharedPreferences("book", MODE_PRIVATE).getBoolean("update", false);
                if(update){
                    homeFragment.reFreshData();
                }
                changeFragmentIndex(item, 0);


                break;
            case R.id.item_download:
                toggleDrawer();
                changeFragmentIndex(item, 1);
                break;

                //收藏图书界面
            case R.id.item_favourite:
                toggleDrawer();
                changeFragmentIndex(item, 2);
                break;
                //浏览图书记录
            case R.id.item_history:
                changeFragmentIndex(item, 3);
                toggleDrawer();
                break;
                //查看已借阅的图书

            //管理界面
            case R.id.item_manager:
                toggleDrawer();
                if(userLevel<2){
                    ToastUtil.showLong(this,"抱歉，只有管理员才能进入");

                }else {
                    changeFragmentIndex(item, 5);
                }
                break;
        }
        return false;
    }

    private void showManagerFragment() {

    }

    /**
     * DrawerLayout侧滑菜单开关
     */
    public void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(mDrawerLayout.getChildAt(1))) {
                mDrawerLayout.closeDrawers();
            } else {
                    exitApp();
            }
        }
        return true;
    }
    /**
     * 双击退出App
     */
    private long exitTime;
    private void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.showShort(this,"再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
//            PreferenceUtil.remove(ConstantUtil.SWITCH_MODE_KEY);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        exitApp();
        super.onBackPressed();

    }

    private int currentTabIndex;
    private int index;

    /**
     * Fragment切换
     */
    private void switchFragment() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.container, fragments[index]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }

    /**
     * 切换Fragment的下标
     */
    private void changeFragmentIndex(MenuItem item, int currentIndex) {
        index = currentIndex;
        switchFragment();
//        item.setChecked(true);
    }


}
