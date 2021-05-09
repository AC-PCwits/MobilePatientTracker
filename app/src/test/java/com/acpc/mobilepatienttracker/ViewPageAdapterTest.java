package com.acpc.mobilepatienttracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.junit.Test;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ViewPageAdapterTest {

    private Fragment frag = new Fragment();
    private final List<Fragment> mFragmentList = new ArrayList<>();
    {
        mFragmentList.add(frag);
    }
    private final List<String> mFragmentTitleList = new ArrayList<>();

    private FragmentManager fragmentManager = new FragmentManager() {
        @NonNull
        @Override
        public FragmentTransaction beginTransaction() {
            return null;
        }

        @Override
        public boolean executePendingTransactions() {
            return false;
        }

        @Nullable
        @Override
        public Fragment findFragmentById(int id) {
            return null;
        }

        @Nullable
        @Override
        public Fragment findFragmentByTag(@Nullable String tag) {
            return null;
        }

        @Override
        public void popBackStack() {

        }

        @Override
        public boolean popBackStackImmediate() {
            return false;
        }

        @Override
        public void popBackStack(@Nullable String name, int flags) {

        }

        @Override
        public boolean popBackStackImmediate(@Nullable String name, int flags) {
            return false;
        }

        @Override
        public void popBackStack(int id, int flags) {

        }

        @Override
        public boolean popBackStackImmediate(int id, int flags) {
            return false;
        }

        @Override
        public int getBackStackEntryCount() {
            return 0;
        }

        @NonNull
        @Override
        public BackStackEntry getBackStackEntryAt(int index) {
            return null;
        }

        @Override
        public void addOnBackStackChangedListener(@NonNull OnBackStackChangedListener listener) {

        }

        @Override
        public void removeOnBackStackChangedListener(@NonNull OnBackStackChangedListener listener) {

        }

        @Override
        public void putFragment(@NonNull Bundle bundle, @NonNull String key, @NonNull Fragment fragment) {

        }

        @Nullable
        @Override
        public Fragment getFragment(@NonNull Bundle bundle, @NonNull String key) {
            return null;
        }

        @NonNull
        @Override
        public List<Fragment> getFragments() {
            return null;
        }

        @Nullable
        @Override
        public Fragment.SavedState saveFragmentInstanceState(@NonNull Fragment f) {
            return null;
        }

        @Override
        public boolean isDestroyed() {
            return false;
        }

        @Override
        public void registerFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks cb, boolean recursive) {

        }

        @Override
        public void unregisterFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks cb) {

        }

        @Nullable
        @Override
        public Fragment getPrimaryNavigationFragment() {
            return null;
        }

        @Override
        public void dump(@NonNull String prefix, @Nullable FileDescriptor fd, @NonNull PrintWriter writer, @Nullable String[] args) {

        }

        @Override
        public boolean isStateSaved() {
            return false;
        }
    };
    private ViewPageAdapter viewPageAdapter = new ViewPageAdapter(fragmentManager);
    {
        viewPageAdapter.addFrag(frag, "frag");
    }

    @Test
    public void testViewPageAdapterObject()
    {
        assertNotNull(new ViewPageAdapter(new FragmentManager() {
            @NonNull
            @Override
            public FragmentTransaction beginTransaction() {
                return null;
            }

            @Override
            public boolean executePendingTransactions() {
                return false;
            }

            @Nullable
            @Override
            public Fragment findFragmentById(int id) {
                return null;
            }

            @Nullable
            @Override
            public Fragment findFragmentByTag(@Nullable String tag) {
                return null;
            }

            @Override
            public void popBackStack() {

            }

            @Override
            public boolean popBackStackImmediate() {
                return false;
            }

            @Override
            public void popBackStack(@Nullable String name, int flags) {

            }

            @Override
            public boolean popBackStackImmediate(@Nullable String name, int flags) {
                return false;
            }

            @Override
            public void popBackStack(int id, int flags) {

            }

            @Override
            public boolean popBackStackImmediate(int id, int flags) {
                return false;
            }

            @Override
            public int getBackStackEntryCount() {
                return 0;
            }

            @NonNull
            @Override
            public BackStackEntry getBackStackEntryAt(int index) {
                return null;
            }

            @Override
            public void addOnBackStackChangedListener(@NonNull OnBackStackChangedListener listener) {

            }

            @Override
            public void removeOnBackStackChangedListener(@NonNull OnBackStackChangedListener listener) {

            }

            @Override
            public void putFragment(@NonNull Bundle bundle, @NonNull String key, @NonNull Fragment fragment) {

            }

            @Nullable
            @Override
            public Fragment getFragment(@NonNull Bundle bundle, @NonNull String key) {
                return null;
            }

            @NonNull
            @Override
            public List<Fragment> getFragments() {
                return null;
            }

            @Nullable
            @Override
            public Fragment.SavedState saveFragmentInstanceState(@NonNull Fragment f) {
                return null;
            }

            @Override
            public boolean isDestroyed() {
                return false;
            }

            @Override
            public void registerFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks cb, boolean recursive) {

            }

            @Override
            public void unregisterFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks cb) {

            }

            @Nullable
            @Override
            public Fragment getPrimaryNavigationFragment() {
                return null;
            }

            @Override
            public void dump(@NonNull String prefix, @Nullable FileDescriptor fd, @NonNull PrintWriter writer, @Nullable String[] args) {

            }

            @Override
            public boolean isStateSaved() {
                return false;
            }
        }));
    }

    @Test
    public void getItem()
    {
        assertEquals(frag, viewPageAdapter.getItem(0));
    }

    @Test
    public void getCount()
    {
        assertEquals(1, viewPageAdapter.getCount());
    }

    @Test
    public void getPageTitle()
    {
        assertEquals("frag", viewPageAdapter.getPageTitle(0));
    }
}