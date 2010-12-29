#include <vector>

#include "unittest/gtest.hpp"

#include "btree/internal_node.hpp"
#include "btree/node.hpp"

namespace unittest {

// TODO: this is rather duplicative of fsck::check_subtree_internal_node.
void verify_internal_node_contiguity(block_size_t block_size, internal_node_t *buf) {




}

TEST(InternalNodeTest, Offsets) {
    EXPECT_EQ(0, offsetof(internal_node_t, magic));
    EXPECT_EQ(4, offsetof(internal_node_t, npairs));
    EXPECT_EQ(6, offsetof(internal_node_t, frontmost_offset));
    EXPECT_EQ(8, offsetof(internal_node_t, pair_offsets));
    EXPECT_EQ(8, sizeof(internal_node_t));

    EXPECT_EQ(0, offsetof(btree_internal_pair, lnode));
    EXPECT_EQ(4, offsetof(btree_internal_pair, key));
    EXPECT_EQ(5, sizeof(btree_internal_pair));
}


}  // namespace unittest

