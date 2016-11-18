/*
 *   Copyright (C) 2016 R&D Solutions Ltd.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *
 */

package io.hawkcd.core.session;

import com.sun.xml.internal.ws.api.server.WSEndpoint;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.hawkcd.ws.WSSession;

/**
 * Created by rado on 11.11.16.
 */
public interface ISessionsPool  {

    Set<WSSession> getSessions();

    WSSession getSessionByID(String id);

    void addSession(WSSession session);

    void removeSession(WSSession session);

    boolean contains(WSSession session);

    WSSession getSessionForUser(String email);

}
