/*
 *  Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.seata.core.protocol.convertor;

import io.seata.core.protocol.protobuf.AbstractGlobalEndRequestProto;
import io.seata.core.protocol.protobuf.AbstractMessageProto;
import io.seata.core.protocol.protobuf.AbstractTransactionRequestProto;
import io.seata.core.protocol.protobuf.GlobalRollbackRequestProto;
import io.seata.core.protocol.protobuf.MessageTypeProto;
import io.seata.core.protocol.transaction.GlobalRollbackRequest;

/**
 * @author leizhiyuan
 */
public class GlobalRollbackRequestConvertor implements PbConvertor<GlobalRollbackRequest, GlobalRollbackRequestProto> {
    @Override
    public GlobalRollbackRequestProto convert2Proto(GlobalRollbackRequest globalRollbackRequest) {
        final short typeCode = globalRollbackRequest.getTypeCode();

        final AbstractMessageProto abstractMessage = AbstractMessageProto.newBuilder().setMessageType(
            MessageTypeProto.forNumber(typeCode)).build();

        final AbstractTransactionRequestProto abstractTransactionRequestProto = AbstractTransactionRequestProto
            .newBuilder().setAbstractMessage(
                abstractMessage).build();

        AbstractGlobalEndRequestProto abstractGlobalEndRequestProto = AbstractGlobalEndRequestProto.newBuilder()
            .setAbstractTransactionRequest(abstractTransactionRequestProto)
            .setXid(globalRollbackRequest.getXid())
            .setExtraData(globalRollbackRequest.getExtraData())
            .build();

        GlobalRollbackRequestProto result = GlobalRollbackRequestProto.newBuilder().setAbstractGlobalEndRequest(
            abstractGlobalEndRequestProto).build();

        return result;
    }

    @Override
    public GlobalRollbackRequest convert2Model(GlobalRollbackRequestProto globalRollbackRequestProto) {
        GlobalRollbackRequest branchCommitRequest = new GlobalRollbackRequest();
        branchCommitRequest.setExtraData(globalRollbackRequestProto.getAbstractGlobalEndRequest().getExtraData());
        branchCommitRequest.setXid(globalRollbackRequestProto.getAbstractGlobalEndRequest().getXid());
        return branchCommitRequest;
    }
}